cmake_minimum_required(VERSION 3.25)
project(boost_demo)

set(CMAKE_CXX_STANDARD 11)

#set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded")
# Windows 下无法使用静态链接, 还要想办法解决一下
set(Boost_USE_STATIC_LIBS ON)
set(Boost_USE_STATIC_RUNTIME ON)

find_package(Boost 1.82.0 COMPONENTS filesystem REQUIRED)
if (Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
    link_directories(${Boost_LIBRARY_DIRS})

    message(STATUS "Boost_INCLUDE_DIRS: ${Boost_INCLUDE_DIRS}")
    message(STATUS "Boost_LIBRARY_DIRS: ${Boost_LIBRARY_DIRS}")
endif ()


add_executable(boost_demo main.cpp)
target_link_libraries(boost_demo
        PRIVATE
        Boost::filesystem
)
