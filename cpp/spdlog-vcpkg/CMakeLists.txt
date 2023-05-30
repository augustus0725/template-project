cmake_minimum_required(VERSION 3.25)
project(spdlog_vcpkg)

set(CMAKE_CXX_STANDARD 11)

find_package(spdlog CONFIG REQUIRED)


add_executable(spdlog_vcpkg main.cpp)
target_link_libraries(spdlog_vcpkg PRIVATE spdlog::spdlog)