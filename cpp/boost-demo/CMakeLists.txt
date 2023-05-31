cmake_minimum_required(VERSION 3.25)
project(boost_demo)

set(CMAKE_CXX_STANDARD 11)

set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded")
# Windows 下无法使用静态链接, 还要想办法解决一下
set(Boost_USE_STATIC_LIBS ON)
set(Boost_USE_STATIC_RUNTIME ON)

macro(get_WIN32_WINNT version)
    if (WIN32 AND CMAKE_SYSTEM_VERSION)
        set(ver ${CMAKE_SYSTEM_VERSION})
        string(REPLACE "." "" ver ${ver})
        string(REGEX REPLACE "([0-9])" "0\\1" ver ${ver})

        set(${version} "0x${ver}")
    endif()
endmacro()

get_WIN32_WINNT(ver)
add_definitions(-D_WIN32_WINNT=${ver})

find_package(Boost 1.82.0 COMPONENTS filesystem thread REQUIRED)
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

add_executable(boost_lambda boost_lambda.cpp)

add_executable(boost_regex boost_regex.cpp)

add_executable(boost_thread_pool boost_thread_pool.cpp)
target_link_libraries(boost_thread_pool PRIVATE Boost::thread)
