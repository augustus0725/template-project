#
cmake -B build -DCMAKE_BUILD_TYPE=Release
cmake -B build -DCMAKE_BUILD_TYPE=Debug

# ����
cmake --build ./build

# ��װ
cmake --build ./build -t install