cmake_minimum_required(VERSION 3.23)
project(c_link_lib C)

set(CMAKE_C_STANDARD 99)

include_directories(
        ${PROJECT_SOURCE_DIR}/include
)

set(LIB_SOURCES
        src/hello.c
)

# lib
add_library(c_link_lib_so STATIC ${LIB_SOURCES})
#add_library(c_link_lib_so SHARED ${LIB_SOURCES})

# exe
add_executable(c_link_lib main.c)

target_link_libraries(c_link_lib
        PRIVATE
        c_link_lib_so
)
