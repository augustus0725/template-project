cmake_minimum_required(VERSION 3.25)
project(log_demo)

set(CMAKE_CXX_STANDARD 11)

include(FetchContent)
FetchContent_Declare(
        spdlog
        URL http://192.168.0.185:15003/repo/cpp/spdlog-1.11.0.zip
)
FetchContent_MakeAvailable(spdlog)

add_executable(log_demo main.cpp)

target_link_libraries(log_demo PRIVATE spdlog)
