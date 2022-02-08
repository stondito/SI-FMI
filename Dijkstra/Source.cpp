#include <iostream>
#include <vector>
#include <utility>
#include <deque>
#include <set>
int n, m, k; 
int INF = (int)1e9;
std::vector<int> shortestPath(std::vector<std::pair<int, int> > adjL[], int& from)
{

    std::vector<int> D(n, INF);
    D[from] = 0;
    std::set<std::pair<int, int> > Q;
    Q.insert({ 0,from });
    while (!Q.empty()) {
        auto top = Q.begin();
        int u = top->second;
        Q.erase(top);
        for (auto next : adjL[u]) {
            int v = next.first, weight = next.second;
            if (D[u] + weight < D[v])
            {
                if (Q.find({ D[v],v }) != Q.end())
                    Q.erase(Q.find({ D[v], v }));

                D[v] = D[u] + weight;
                Q.insert({ D[v], v });
            }
        }
    }
    return D;
}

std::vector<std::pair<int, int> > adjL[2000000];
int main()
{
    std::ios::sync_with_stdio(false);
    std::cin.tie(NULL);

    std::cin >> n >> m >> k;
    int xi, yi;
    for (int i = 0; i < m; ++i)
    {
        std::cin >> xi >> yi;
        adjL[xi].push_back({ yi, 0 });
        adjL[yi].push_back({ xi, 0 });
    }

    for (int i = 0; i < k; ++i)
    {
        std::cin >> xi >> yi;
        adjL[xi].push_back({ yi, 1 });
        adjL[yi].push_back({ xi, 1 });
    }

    std::vector<int> ans;
    int serch = 0;
    ans = shortestPath(adjL, serch);

    if (ans[n - 1] != INF) {
        std::cout << ans[n - 1];
    }
    else
        std::cout << -1;


    return 0;
}