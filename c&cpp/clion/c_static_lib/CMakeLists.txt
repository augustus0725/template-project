cmake_minimum_required(VERSION 3.23)
project(c_static_lib C)

set(CMAKE_C_STANDARD 99)

add_library(c_static_lib library.c)
