cmake_minimum_required(VERSION 3.23)
project(c_include_header C)

set(CMAKE_C_STANDARD 99)
set(SOURCES
        hello.c
        main.c
)

add_executable(c_include_header ${SOURCES})

# when running g++ these will be included as -I/directory/path/
target_include_directories(c_include_header
#        ?
        PRIVATE
        ${PROJECT_SOURCE_DIR}/include
)
