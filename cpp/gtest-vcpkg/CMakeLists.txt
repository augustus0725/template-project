cmake_minimum_required(VERSION 3.25)
project(gtest_vcpkg)

set(CMAKE_CXX_STANDARD 11)

add_library(hello_library STATIC
        ${PROJECT_SOURCE_DIR}/src/hello.cpp)
target_include_directories(hello_library PUBLIC ${PROJECT_SOURCE_DIR}/src/include)

enable_testing()

find_package(GTest CONFIG REQUIRED)
message(STATUS "Using GTest ${GTest_VERSION}")
add_executable(
        hello_test
        ${PROJECT_SOURCE_DIR}/src/hello_test.cpp)
target_link_libraries(
        hello_library
        GTest::gtest_main
)

target_include_directories(hello_test PRIVATE
        ${PROJECT_SOURCE_DIR}/src/include
#        D:/workspace/remote-github/vcpkg/installed/x64-windows/include
        )

message(STATUS ${INCLUDE_DIRS})

include(GoogleTest)
gtest_discover_tests(hello_test)
