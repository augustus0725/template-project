cmake_minimum_required(VERSION 3.17)
project(c_app_inst C)

set(CMAKE_C_STANDARD 99)

add_executable(c_app_inst main.c)

# inst
install(TARGETS c_app_inst DESTINATION bin)

# Header files
#install(DIRECTORY ${PROJECT_SOURCE_DIR}/include/
#        DESTINATION include)

# Config
#install (FILES cmake-examples.conf
#        DESTINATION etc)
