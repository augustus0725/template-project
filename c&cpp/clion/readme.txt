#
mkdir build
cd build
cmake .. -DCMAKE_BUILD_TYPE=Release
cmake .. -DCMAKE_BUILD_TYPE=Debug

# ����
cmake --build . --target c_app_inst

# ��װ
cmake --build . --target install