cmake_minimum_required(VERSION 3.23)
project(cpp_use_boost)

set(CMAKE_CXX_STANDARD 14)
set(Boost_USE_STATIC_LIBS        ON)
find_package(Boost 1.79.0 REQUIRED COMPONENTS filesystem system)

add_executable(cpp_use_boost main.cpp)
