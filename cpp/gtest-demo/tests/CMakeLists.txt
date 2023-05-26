if (ENABLE_TEST)
    include(FetchContent)
    FetchContent_Declare(
            googletest
            URL http://192.168.0.185:15003/repo/cpp/googletest-1.13.0.zip
    )
    set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
    FetchContent_MakeAvailable(googletest)

    enable_testing()

    add_executable(
            hello_test
            ${PROJECT_SOURCE_DIR}/tests/hello_test.cpp)
    target_link_libraries(
            hello_test
            hello_library
            GTest::gtest_main
    )

    include(GoogleTest)
    gtest_discover_tests(hello_test)
endif ()
