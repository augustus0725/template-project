#include <boost/asio.hpp>
#include <iostream>
#include <boost/chrono.hpp>
#include <boost/thread/thread.hpp>


int main() {
    boost::asio::thread_pool pool(4);

    for (int i = 0; i < 100; ++i) {
        boost::asio::post(pool, [=]() {
            std::cout << "hello world: " << i << std::endl;
            boost::this_thread::sleep_for(boost::chrono::milliseconds(10));
        });
    }
    pool.join();
}