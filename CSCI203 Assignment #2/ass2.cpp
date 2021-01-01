/*
  Samuel Ian Black
  SIB979
  6025821
  CSCI203
  Assignment 2
*/

#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>

using namespace std;

const int cMaxCustQueue = 500;
const int cMaxEveQueue = 101;
const int cMaxServerQueue = 21;

struct Customer
{
	double arrivalTime;
	double tallyTime;
	double paymentType;
};

struct Event
{
	int type;
	double time, tallyTime;
	double paymentType;
};

struct Server
{
	Server();
	double efficiencyFactor;
	int numCustomersServed;
	int serverNumber;
	double idleTime;
};

Server::Server()
{
	numCustomersServed = 0;
	idleTime = 0;
}

class CustomerQueue
{
public:
	CustomerQueue();
	void enQueue(double arrivalTime, double tallyTime, double paymentType);
	void deQueue(double& arrivalTime, double& tallyTime, double& paymentType);
	bool isEmpty();
	int inIdx, outIdx, numCustomers;
	Customer queue[cMaxCustQueue];
};

CustomerQueue::CustomerQueue()
{
	inIdx = outIdx = numCustomers = 0;
}

class EventQueue
{
public:
	EventQueue();
	void enQueue(int type, double arrivalTime, double tallyTime, double paymentType);
	Event deQueue();
	bool isEmpty();

	void minHeapify(int i);
	void swap(int* x, int* y);
	Event queue[cMaxEveQueue];
	int numEvents;
};

EventQueue::EventQueue()
{
	numEvents = 0;
}

class ServerArray
{
public:
	ServerArray();
	Server serverArray[cMaxServerQueue];
	int idleServerPosition[cMaxServerQueue];
	int numServers;
	int numIdleServers;

	void insertServer(double efficiency);
	void swapServer(int* x, int* y);

	void insertIdle(double position);
	void minHeapify(int i);
	void extractMin();
	void swapIdle(int* a, int* b);
};

ServerArray::ServerArray()
{
	numServers = 0;
	numIdleServers = 0;
}

int main()
{
	ifstream inFile;

	CustomerQueue* cq = new CustomerQueue; // Initialize pointers to queues and structs
	ServerArray* sa = new ServerArray;
	EventQueue* eq = new EventQueue;
	Event* eve = new Event;
	Server* ser = new Server;
	Customer* cust = new Customer;

	double val1 = 0;//Variables used in file reading.
	double val2 = 0;
	string val3;

	double customersServedCount = 0;
	int totalCustQueued = 0;
	int longestCustQueueLength = 0;

	double totalCustomerWaitingTime = 0;// Used in calculating average customer waiting time in queue.

	double queueLengthWA = 0; //used for calculating average queue length.
	double lastTime = 0; //Used to hold the last time the customer queue was n length for calculating average queue length.

	inFile.open("ass2.txt");

	if (inFile.bad()) { cerr << "Error opening/reading file."; exit(1); }

	int readNumOfServers = 0;
	inFile >> readNumOfServers;

	for (int i = 0; i < readNumOfServers; i++)//Read servers from file.
	{
		inFile >> val1;
		sa->insertServer(val1);
	}

	for (int i = 1; i < sa->numServers + 1; i++)//Initialize my server position array, O(n).
	{
		sa->idleServerPosition[i] = i;
		sa->numIdleServers++;
	}

	inFile >> val1;//Read first line from file.
	inFile >> val2;
	inFile >> val3;

	if (val3 == "card")
	{
		eq->enQueue(-1, val1, val2, 0.7);
	}
	else
		eq->enQueue(-1, val1, val2, 0.3);
	customersServedCount++;

	while (!eq->isEmpty())//Start of Discrete time simulation.
	{
		*eve = eq->deQueue();
		double currentTime = eve->time;
		if (eve->type < 0)
		{
			if (sa->numIdleServers >= 1)
			{
				int pos = sa->idleServerPosition[1];//Get position of fastest idle server.
				*ser = sa->serverArray[pos];
				sa->serverArray[pos].numCustomersServed++;
				sa->extractMin();//Remove position of server from heap.
				double serviceTime = (eve->tallyTime * ser->efficiencyFactor) + eve->paymentType + currentTime;

				eq->enQueue(pos, serviceTime, currentTime, 0);//Current time is stored in server finish event to calculate idle time.
			}
			else
			{
				cq->enQueue(eve->time, eve->tallyTime, eve->paymentType);

				queueLengthWA += cq->numCustomers * (currentTime - lastTime);//Calc average queue length.
				totalCustQueued++;//Increment total customers queued, used for calculating average wait time.

				if (cq->numCustomers > longestCustQueueLength)//Calculate longest length of customer queue.
				{
					longestCustQueueLength = cq->numCustomers;
				}

				lastTime = currentTime;//set lastTime to currentTime for calculating average customer queue length.
			}
			if (inFile >> val1 >> val2 >> val3)
			{
				if (val3 == "card")
				{
					eq->enQueue(-1, val1, val2, 0.7);
				}
				else
					eq->enQueue(-1, val1, val2, 0.3);
				customersServedCount++;
			}
		}
		else
		{
			int pos = eve->type;
			sa->serverArray[pos].idleTime += eve->time - eve->tallyTime;
			sa->insertIdle(pos);//Insert position of server back into int array since no longer busy.

			if (!cq->isEmpty())
			{
				*cust = cq->queue[cq->outIdx];

				totalCustomerWaitingTime += currentTime - cust->arrivalTime; //Calc % of customers who did not queue.
				queueLengthWA += cq->numCustomers * (currentTime - lastTime); //Calc average queue length.

				int serverPos = sa->idleServerPosition[1];//Get position of fastest efficiency server.
				*ser = sa->serverArray[serverPos];//pointer to fastest server.
				sa->serverArray[serverPos].numCustomersServed++;
				sa->extractMin();//Remove position of fastest server from array since now busy.

				double serviceTime = (cust->tallyTime * ser->efficiencyFactor) + cust->paymentType + currentTime;//Calculate service time.

				eq->enQueue(serverPos, serviceTime, currentTime, 0);//Current time is stored in server finish event to calculate idle time.
				cq->deQueue(cust->arrivalTime, cust->paymentType, cust->tallyTime);

				lastTime = currentTime;//set lastTime to currentTime for calculating average customer queue length.
			}
		}
	}
	double endTime = 0;
	if (eq->isEmpty())
	{
		endTime = eve->time; //Total simulation time.
	}

	inFile.close();

	double numCustomersNoWait = customersServedCount - totalCustQueued; //Calculate number of customers who did not wait in queue.
	double customersPercent = numCustomersNoWait / customersServedCount * 100; //Calculate percentage of customers who did not wait in queue.

	cout << "Number of customers served: " << customersServedCount << endl;
	cout << "Time taken to serve all customers: " << endTime << endl;
	cout << "Greatest length reached by the customer queue: " << longestCustQueueLength << endl;
	cout << "Average length of the customer queue: " << queueLengthWA / endTime << endl;
	cout << "Average customer waiting time in queue: " << totalCustomerWaitingTime / totalCustQueued << endl;
	cout << "Percentage of customers with zero waiting time: " << customersPercent << "%" << endl << endl;

	int width = 25;

	cout << "Server" << setw(width) << "Priority" << setw(width) << "Customers Served" << setw(width) << "Idle Time" << endl;
	for (int i = 1; i < sa->numServers + 1; i++)
	{
		cout << setw(4) << i << setw(width) << sa->serverArray[i].efficiencyFactor << setw(20) << sa->serverArray[i].numCustomersServed << setw(31) << endTime - sa->serverArray[i].idleTime << endl;
	}

	return 0;
}


/******************Customer Queue**********************/
void CustomerQueue::enQueue(double arrivalTime, double tallyTime, double paymentType) //Put customer in circular queue.
{
	if (numCustomers == cMaxCustQueue) { cerr << "Customer queue is full."; return; }
	queue[inIdx].arrivalTime = arrivalTime;
	queue[inIdx].tallyTime = tallyTime;
	queue[inIdx].paymentType = paymentType;
	inIdx++;
	if (inIdx == cMaxCustQueue) { inIdx = 0; }
	numCustomers++;
}

void CustomerQueue::deQueue(double& arrivalTime, double& tallyTime, double& paymentType) //Remove customer from circular queue.
{
	if (numCustomers == 0) { cerr << "Customer queue is empty."; exit(1); }

	arrivalTime = queue[outIdx].arrivalTime;
	tallyTime = queue[outIdx].tallyTime;
	paymentType = queue[outIdx].paymentType;

	outIdx++; if (outIdx == cMaxCustQueue) { outIdx = 0; }
	numCustomers--;
}

bool CustomerQueue::isEmpty()
{
	if (numCustomers == 0) { return true; }
	return false;
}


/******************Event Queue**********************/
void EventQueue::enQueue(int type, double arrivalTime, double tallyTime, double paymentType)//Add event to event queue and maintain min heap.
{
	if (numEvents == cMaxCustQueue)
	{
		cerr << "Event Queue is full." << endl;
		return;
	}

	numEvents++;
	int i = numEvents;
	queue[i].type = type;
	queue[i].time = arrivalTime;
	queue[i].tallyTime = tallyTime;
	queue[i].paymentType = paymentType;

	while (i != 1 && queue[i / 2].time > queue[i].time)
	{
		int parent = i / 2;
		swap(&i, &parent);
		i = i / 2;
	}
}

Event EventQueue::deQueue()//Remove event and return it from event queue and maintain min heap
{
	if (numEvents <= 0)
	{
		cerr << "Event Queue is empty." << endl;
	}
	Event root;
	root = queue[1];
	queue[1] = queue[numEvents];
	numEvents--;
	minHeapify(1);

	return root;
}

bool EventQueue::isEmpty()
{
	if (numEvents == 0) { return true; }
	return false;
}

void EventQueue::minHeapify(int i)//Sift down Event queue based on lowest time value
{
	int left = i * 2;
	int right = (i * 2) + 1;
	int smallest = i;
	if (left < numEvents + 1 && queue[left].time < queue[i].time)
		smallest = left;
	if (right < numEvents + 1 && queue[right].time < queue[smallest].time)
		smallest = right;
	if (smallest != i)
	{
		swap(&i, &smallest);
		minHeapify(smallest);
	}
}

void EventQueue::swap(int* x, int* y)
{
	Event temp = queue[*x];
	queue[*x] = queue[*y];
	queue[*y] = temp;
}


//****************Server Array**************************//
void ServerArray::insertServer(double efficiency)//Insert server into server array and siftup while loop to sort based on efficiency.
{
	if (numServers >= cMaxServerQueue)
	{
		cerr << "Servers are full." << endl;
		return;
	}

	numServers++;
	int i = numServers;
	serverArray[i].efficiencyFactor = efficiency;

	while (i != 1 && serverArray[i / 2].efficiencyFactor > serverArray[i].efficiencyFactor)
	{
		int parent = i / 2;
		swapServer(&i, &parent);
		i = i / 2;
	}
}

void ServerArray::insertIdle(double position)//Insert position of idle server into min heap array and sift up in while loop.
{
	numIdleServers++;
	idleServerPosition[numIdleServers] = position;

	int i = numIdleServers;
	while (i != 1 && idleServerPosition[i / 2] > idleServerPosition[i])
	{
		int parent = i / 2;
		swapIdle(&i, &parent);
		i = i / 2;
	}
}

void ServerArray::minHeapify(int i) //Sift down server position integer array, not server array.
{
	int left = i * 2;
	int right = (i * 2) + 1;
	int smallest = i;
	if (left < numIdleServers + 1 && idleServerPosition[left] < idleServerPosition[i]) {
		smallest = left;
	}
	if (right < numIdleServers + 1 && idleServerPosition[right] < idleServerPosition[smallest]) {
		smallest = right;
	}
	if (smallest != i)
	{
		swapIdle(&i, &smallest);
		minHeapify(smallest);
	}
}

void ServerArray::extractMin() //Remove position of idle server from min heap.
{
	if (numServers <= 0)
	{
		cout << "No Servers available, something went wrong."; exit(1);
	}
	if (numIdleServers <= 0)
	{
		cerr << "All Servers are busy." << endl;
		return;
	}

	idleServerPosition[1] = idleServerPosition[numIdleServers];
	numIdleServers--;
	minHeapify(1);
}

void ServerArray::swapIdle(int* a, int* b)//This swap is used for the idle server position integer array
{
	int t = idleServerPosition[*a];
	idleServerPosition[*a] = idleServerPosition[*b];
	idleServerPosition[*b] = t;
}

void ServerArray::swapServer(int* x, int* y)//This swap is used for the actual Server array
{
	Server temp = serverArray[*x];
	serverArray[*x] = serverArray[*y];
	serverArray[*y] = temp;
}

/*
All enhancements & algorithms used are listed below.
Step #1: Customer Queue uses circular queue for O(1) efficiency since queue is FIFO.
Step #2: Event Queue uses a Min Heap for O(log n) efficiency of getting the next event based on lowest time.
Step #3: Server Array reads the servers from the file, and sorts them in order of lowest efficiency using a sift up while loop in the insert method O(log n).
		 I then initialize an integer array the size of n to represent the positions of the sorted servers O(n). To always get the most efficient idle server I
		 min-heapify the integer array based on lowest position and extract the position after doing so, the position is added back to the integer array once
		 it pops out of the event queue. This saves extra checks for whether or not the server is also idle.
*/