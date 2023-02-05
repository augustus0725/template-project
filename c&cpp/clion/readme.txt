#
mkdir build
cd build
cmake .. -DCMAKE_BUILD_TYPE=Release
cmake .. -DCMAKE_BUILD_TYPE=Debug

# ±àÒë
cmake --build . --target c_app_inst

# °²×°
cmake --build . --target install