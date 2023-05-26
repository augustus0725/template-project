target_include_directories(hello_library
        PRIVATE
        ${PROJECT_SOURCE_DIR}/src/include
        )

target_link_libraries( hello_binary
        PRIVATE
        hello_library
        )