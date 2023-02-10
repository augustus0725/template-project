cmake_minimum_required(VERSION 3.23)
project(cpp_use_boost)

set(CMAKE_CXX_STANDARD 14)
find_package(Boost 1.81.0 REQUIRED COMPONENTS filesystem system)

add_executable(cpp_use_boost main.cpp)
