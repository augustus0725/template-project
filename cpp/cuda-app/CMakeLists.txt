cmake_minimum_required(VERSION 3.25)
project(cuda_app CUDA)

set(CMAKE_CUDA_STANDARD 11)

add_executable(cuda_app main.cu)

set_target_properties(cuda_app PROPERTIES
        CUDA_SEPARABLE_COMPILATION ON)
