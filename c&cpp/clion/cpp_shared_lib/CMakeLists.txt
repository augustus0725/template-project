cmake_minimum_required(VERSION 3.23)
project(cpp_shared_lib)

set(CMAKE_CXX_STANDARD 14)

add_library(cpp_shared_lib SHARED library.cpp)
