#include <iostream>
#include <string>
#include <algorithm>

bool is_palindrom(std::string& arr, const int& len)
{
    int count = 0;
    int temp = 0;
    int index = 0;
    for (int i = 0; i < len; i += count)
    {
        count = 0;
        do
        {
            ++index;
            ++count;
        } while (arr[index - 1] == arr[index]);

        if (count % 2 != 0)
        {
            ++temp;
        }

        if (temp == 2)
        {
            return false;
        }

    }

    return true;
}

std::string str[23];

int main()
{
    std::ios::sync_with_stdio(false);
    std::cin.tie(NULL);

    int n = 0;
    std::cin >> n;

    for (int i = 0; i < n; ++i)
    {
        std::cin >> str[i];
    }


    for (int i = 0; i < n; i++) 
    {
        std::sort(str[i].begin(), str[i].end());
    }

    for (int i = 0; i < n; i++)
    {
        if (is_palindrom(str[i], str[i].length()))
        {
            std::cout << "TRUE" << std::endl;
        }
        else
            std::cout << "FALSE" << std::endl;
    }




    return 0;
}