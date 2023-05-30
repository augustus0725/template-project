cmake_minimum_required(VERSION 3.25)
project(gtest_vcpkg)

set(CMAKE_CXX_STANDARD 11)

include_directories(
        ${PROJECT_SOURCE_DIR}/src/include
)

add_library(hello_library STATIC
        ${PROJECT_SOURCE_DIR}/src/hello.cpp)

enable_testing()

find_package(GTest CONFIG REQUIRED)
message(STATUS "Using GTest ${GTest_VERSION}")

add_executable(hello_test
        ${PROJECT_SOURCE_DIR}/src/hello_test.cpp)
target_link_libraries(hello_test
        hello_library
        GTest::gtest_main
)

include(GoogleTest)
gtest_discover_tests(hello_test)
