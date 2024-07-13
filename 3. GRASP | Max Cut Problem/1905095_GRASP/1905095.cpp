#include <iostream>
#include <vector>
#include <list>
#include <map>
#include <set>
#include <random>
#include <algorithm>
#define ll long long int

using namespace std;
ll maxIterations = 20; // maxIterations for GRASP

class Edges{
    ll src;
    ll dest;
    ll weight;
public:
    void setEdges(ll src, ll dest, ll weight){
        this->src=src;
        this->dest=dest;
        this->weight= weight;
    }
    ll getSrc(){ return this->src;}
    ll getDest(){return this->dest;}
    ll getWeight(){return this->weight;}
};

class Graph
{
    ll V, E;
    set<ll> X, Y; // Partitions
    list< pair<ll, ll> > *adj;    
    vector<ll> randomMaxCutValues; 
    vector<ll> simpleGreedyMaxCutValues; 
    vector<ll> semiGreedyMaxCutValues;
    vector<ll> localSearchValues; 
    ll maxCut; 
    ll randomAverage;
    ll simpleGreedyAverage; 
    ll semiGreedyAverage; 
    ll simpleLocalIterations; 
    ll simpleLocalAverage; 

public:
    Edges* edge;
    
    Graph(ll V, ll E);
    
    void addEdge(ll u, ll v, ll w);   
    void greedyMaxCut(double alpha);
    void randomMaxCut(); 
    void simpleGreedyMaxCut(); 
    void semiGreedyMaxCut();
    void localSearch();
    void GRASP(); 
    ll getSumX(ll v);
    ll getSumY(ll v); 
    ll calculateMaxCut(); // calculate maxCut of current partition
    ll getRandomAverage(); // get average of randomMaxCutValues
    ll getSimpleGreedyAverage(); // get average of simpleGreedyMaxCutValues 
    ll getSemiGreedyAverage();  // get average of semiGreedyMaxCutValues
    ll getSimpleLocalIterations();  // get average of simpleLocalIterations
    ll getSimpleLocalAverage(); // get average of localSearchValues
    void printValues(); 
};



Graph::Graph(ll V, ll E)
{
    this->V = V;
    this->E = E;
    adj = new list<pair<ll , ll> > [V];
    this->edge= new Edges[E];
    this->simpleLocalIterations=0; 
}

void Graph::addEdge(ll u, ll v, ll w)
{
    adj[u].push_back(make_pair(v, w));
}

ll Graph::getSumX(ll v){ // get sum of weights of edges from v to Y
    ll sumX = 0;
    for(auto it: adj[v])
    {
        if(Y.find(it.first) != Y.end()){
            sumX += it.second;
        }
    }
    return sumX;
}

ll Graph::getSumY(ll v){ // get sum of weights of edges from v to X
    ll sumY = 0;
    for(auto it: adj[v])
    {
        if(X.find(it.first) != X.end()){
            sumY += it.second;
        }
    }
    return sumY;
}

ll Graph::calculateMaxCut(){
    ll cutWeight = 0;
    for(ll i=0; i<E; i++){
        if(X.find(edge[i].getSrc()) != X.end() && Y.find(edge[i].getDest()) != Y.end() || 
           X.find(edge[i].getDest()) != X.end() && Y.find(edge[i].getSrc()) != Y.end()){
            cutWeight += edge[i].getWeight();
        }
    }
    return cutWeight;
}



void Graph::greedyMaxCut(double alpha){
    X.clear();
    Y.clear(); 
    double a = alpha; 
    ll wmin = INT64_MAX;
    ll wmax = INT64_MIN; 
    for(ll i=0; i<E; i++){
        if(edge[i].getWeight() < wmin){
            wmin = edge[i].getWeight();
        }
        if(edge[i].getWeight() > wmax){
            wmax = edge[i].getWeight();
        }
    }
    // cout<<wmin<<" "<<wmax<<endl;
    double u = wmin + a*(wmax-wmin); // u is the cut-off weight
    vector<ll> RCLeIndices; 
    for(ll i=0; i<E; i++){
        if(edge[i].getWeight() >= u){
            RCLeIndices.push_back(i);
        }
    }
    ll randomIndex = rand() % RCLeIndices.size();
    ll randomEdgeIndex = RCLeIndices[randomIndex]; 
    ll randomEdgeSrc = edge[randomEdgeIndex].getSrc();
    ll randomEdgeDest = edge[randomEdgeIndex].getDest(); 
    X.insert(randomEdgeSrc);
    Y.insert(randomEdgeDest);
    
    set<ll> VPrime; // VPrime = V - (X U Y)
    while(X.size() + Y.size() != V){
        VPrime.clear();
        for(ll i=0; i<V; i++){
            if(X.find(i) == X.end() && Y.find(i) == Y.end()){
                VPrime.insert(i);
            }
        }
        ll sumX = 0; 
        ll sumY = 0;
        vector < pair<ll,ll> > sx, sy;
        for(int v : VPrime){
            for(auto it: adj[v]){
                if(X.find(it.first) != X.end()){ // if it is in X
                    sumY += it.second;
                }
                if(Y.find(it.first) != Y.end()){
                    sumX += it.second;
                }
            }
            sx.push_back(make_pair(v, sumX));
            sy.push_back(make_pair(v, sumY));
            sumX = 0;
            sumY = 0;
        }
        ll maxSX = INT64_MIN;
        ll maxSY = INT64_MIN;
        ll maxSXIndex = -1;
        ll maxSYIndex = -1;
        for(ll i=0; i<sx.size(); i++){
            if(sx[i].second > maxSX){
                maxSX = sx[i].second;
                maxSXIndex = i;
            }
            if(sy[i].second > maxSY){
                maxSY = sy[i].second;
                maxSYIndex = i;
            }
        }
        ll minSX = INT64_MAX;
        ll minSY = INT64_MAX;
        ll minSXIndex = -1;
        ll minSYIndex = -1;
        for(ll i=0; i<sx.size(); i++){
            if(sx[i].second < minSX){
                minSX = sx[i].second;
                minSXIndex = i;
            }
            if(sy[i].second < minSY){
                minSY = sy[i].second;
                minSYIndex = i;
            }
        }
        wmin = min(minSX, minSY);
        wmax = max(maxSX, maxSY);
        u = wmin + a*(wmax-wmin);
        vector<ll> RCLvIndices;
        RCLvIndices.clear();
        // cout<<"VPrime.size(): "<<VPrime.size()<<endl;
        for(int v: VPrime){
            ll sxv = 0;
            ll syv = 0;
            for(ll i=0; i<sx.size(); i++){
                if(sx[i].first == v){
                    sxv = sx[i].second;
                }
                if(sy[i].first == v){
                    syv = sy[i].second;
                }
            }
            ll maxsv = max(sxv, syv);
            if(maxsv >= u){
                RCLvIndices.push_back(v);
            }
        }
        if(RCLvIndices.size() == 0){
            continue;
        }
        randomIndex = rand() % RCLvIndices.size();
        ll randomVIndex = RCLvIndices[randomIndex];
        ll randomV = randomVIndex;
        if(getSumX(randomV) > getSumY(randomV)){
            X.insert(randomV);
        }
        else{
            Y.insert(randomV);
        }
    }
}

void Graph::semiGreedyMaxCut(){
    random_device rd;
    mt19937 gen(rd());
    uniform_real_distribution<double> dist(0.0, 1.0);
    double a = dist(gen); 
    greedyMaxCut(a);
    ll cutWeight = calculateMaxCut();
    semiGreedyMaxCutValues.push_back(cutWeight);
}

void Graph::randomMaxCut(){
    greedyMaxCut(0.0); 
    ll cutweight = calculateMaxCut(); 
    randomMaxCutValues.push_back(cutweight); 
}

void Graph::simpleGreedyMaxCut(){
    greedyMaxCut(1.0); 
    ll cutweight = calculateMaxCut(); 
    simpleGreedyMaxCutValues.push_back(cutweight); 
}


void Graph::localSearch(){
    bool change = true;
    while(change){
        change = false;
        simpleLocalIterations++; 
        for(ll i=0 ; i<V; i++){
            if(change) break; 
            if(X.find(i)!= X.end()){ // 
                if(getSumX(i) < getSumY(i)){
                    X.erase(i);
                    Y.insert(i);
                    change = true;
                }
            }
            else{
                if(getSumX(i) > getSumY(i)){
                    Y.erase(i);
                    X.insert(i);
                    change = true;
                }
            }
        }
    }
    localSearchValues.push_back(calculateMaxCut()); 
}

void Graph::GRASP(){
    maxCut = INT64_MIN;
    for(ll i=0; i<maxIterations; i++){
        randomMaxCut();
        if(i==0) simpleGreedyMaxCut();
        semiGreedyMaxCut();
        localSearch();
        if(i==0){
            maxCut = calculateMaxCut();
        }
        else{
            ll cutWeight = calculateMaxCut();
            if(cutWeight > maxCut){
                maxCut = cutWeight;
            }
        }
    }
}

ll Graph::getRandomAverage(){
    ll average = 0; 
    ll size = randomMaxCutValues.size();
    for(ll i=0; i<size; i++){
        average+=randomMaxCutValues[i]; 
    }
    average/=size; 
    return average; 
}

ll Graph::getSimpleGreedyAverage(){
    ll average = 0; 
    ll size = simpleGreedyMaxCutValues.size();
    for(ll i=0; i<size; i++){
        average+=simpleGreedyMaxCutValues[i]; 
    }
    average/=size; 
    return average; 
}

ll Graph::getSemiGreedyAverage(){
    ll average = 0; 
    ll size = semiGreedyMaxCutValues.size();
    for(ll i=0; i<size; i++ ){
        average+=semiGreedyMaxCutValues[i];
    }
    average/=size; 
    return average; 
}

ll Graph::getSimpleLocalIterations(){
    return simpleLocalIterations/maxIterations; 
}

ll Graph::getSimpleLocalAverage(){
    ll average = 0; 
    ll size = localSearchValues.size();
    for(ll i=0; i<size; i++){
        average+=localSearchValues[i]; 
    }
    average/=size; 
    return average; 
}

void Graph::printValues(){
    cout<<V<<",";
    cout<<E<<",";
    cout<<getRandomAverage()<<",";
    cout<<getSimpleGreedyAverage()<<",";
    cout<<getSemiGreedyAverage()<<",";
    cout<<getSimpleLocalIterations()<<",";
    cout<<getSimpleLocalAverage()<<",";
    cout<<maxIterations<<",";
    cout<<maxCut<<endl; 
}

int main(int argc, char* argv[]){

    // string inputfile = string(argv[1]);
    // cout<<inputfile<<",";

    ll nodes,edges;
    cin>>nodes>>edges;
    ll u,v;
    ll w;
    Graph graphVector(nodes,edges);
    
    for(ll i=0;i<edges;i++){
        cin>>u>>v>>w;
        graphVector.edge[i].setEdges(u-1,v-1,w);
        graphVector.addEdge(u-1, v-1, w);
        graphVector.addEdge(v-1, u-1, w); // for undirected graph
    }
    graphVector.GRASP();
    graphVector.printValues(); 


    
    return 0;
}
