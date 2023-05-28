# 下载grpc代码, 可以用vs + cmake构建, 但是没有vcpkg方便
git clone -b v1.55.0 https://github.com/grpc/grpc
cd grpc
git submodule update --init
-DCMAKE_BUILD_TYPE=Release -DgRPC_INSTALL=ON -DgRPC_BUILD_TESTS=OFF -DCMAKE_INSTALL_PREFIX=/opt/install


# windows 和 linux都用vcpkg 管理vc 的grpc包
==> ubuntu
apt-get install build-essential autoconf libtool pkg-config clang libc++-dev -y
git clone https://github.com/Microsoft/vcpkg.git
cd vcpkg/
./bootstrap-vcpkg.sh
./vcpkg install grpc:x64-linux

==> windows
git clone https://github.com/Microsoft/vcpkg.git
cd vcpkg/
./bootstrap-vcpkg.bat
./vcpkg.exe install grpc:x64-windows


        
vcpkg的方式:
1. cmake先关联vcpkg, 让vcpkg管理vc package
2. 然后find_package 进行关联grpc
        
# linux       
cmake -B build -S . -DCMAKE_TOOLCHAIN_FILE=/opt/vcpkg/scripts/buildsystems/vcpkg.cmake
# windows
cmake -B build2 -S . -DCMAKE_TOOLCHAIN_FILE=D:/workspace/remote-github/vcpkg/scripts/buildsystems/vcpkg.cmake

Stored binaries in 1 destinations.
Elapsed time to handle grpc:x64-linux: 1.4 h
Total install time: 2 h
grpc provides CMake targets:

    # this is heuristically generated, and may not be correct
    find_package(gRPC CONFIG REQUIRED)
    # note: 7 additional targets are not displayed.
    target_link_libraries(main PRIVATE gRPC::gpr gRPC::grpc gRPC::grpc++ gRPC::grpc++_alts)