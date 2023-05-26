add_executable(gtest_demo main.cpp)

add_library(hello_library STATIC include/hello.h hello.cpp)
target_include_directories(hello_library PUBLIC ${PROJECT_SOURCE_DIR}/src/include)

target_include_directories(gtest_demo PRIVATE ${PROJECT_SOURCE_DIR}/src/include)
target_link_libraries(gtest_demo PRIVATE hello_library)