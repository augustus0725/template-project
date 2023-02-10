#
cmake -B build -DCMAKE_BUILD_TYPE=Release
cmake -B build -DCMAKE_BUILD_TYPE=Debug

# ±àÒë
cmake --build ./build

# °²×°
cmake --build ./build -t install