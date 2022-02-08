#include <iostream>
#include <cmath>

int main()
{
	std::ios::sync_with_stdio(false);
	std::cin.tie(NULL);

	int n = 0;
	std::cin >> n;

	int* arr = new int[n];

	for (int i = 0; i < n; ++i)
	{
		std::cin >> arr[i];
	}

	int min = 10000000000; 
	bool* is_used = new bool[n];
	int index = 0;

	for (int i = 0; i < n; ++i)
	{
		if (arr[i] >= 0)
		{
			is_used[i] = true; 
			if (arr[i] < min) 
			{
				min = arr[i];
				index = i;
			}
		}
		else
			is_used[i] = false; 
	}

	for (int i = 0; i < n; ++i)
	{
		if (is_used[i] == true)
		{
			if (i != index && arr[i] - min == 1) 
			{
				min = arr[i];
				index = i;
				is_used[i] = false;
				i = 0;
			}
		}
	}

	std::cout << arr[index] + 1;  

	delete[] is_used;
	delete[] arr;

	return 0;
}