#include <boost/lambda/lambda.hpp>
#include <iostream>
#include <iterator>
#include <algorithm>
#include <vector>

int main()
{
    std::vector<int> vec;

    vec.push_back(1);
    vec.push_back(2);
    vec.push_back(3);

    for (auto iter = vec.begin(); iter != vec.end(); iter++) {
        std::cout << *iter << std::endl;
    }

    using namespace boost::lambda;
    typedef std::istream_iterator<int> in;

    std::for_each(
            in(std::cin), in(), std::cout << (_1 * 3) << " " );
}