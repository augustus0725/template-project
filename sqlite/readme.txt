# 代码下载
fossil clone https://www.sqlite.org/src sqlite.fossil

# sqlite.fossil 是个100MB+的文件
fossil open sqlite.fossil

# 切换branch
fossil checkout branch-3.42

# checkout 指定版本的代码 such as "version-3.8.8"
fossil update VERSION

# 构建sqlite
    tar xzf sqlite.tar.gz    ;#  Unpack the source tree into "sqlite"
    mkdir bld                ;#  Build will occur in a sibling directory
    cd bld                   ;#  Change to the build directory
    ../sqlite/configure      ;#  Run the configure script
    make                     ;#  Builds the "sqlite3" command-line tool
    make sqlite3.c           ;#  Build the "amalgamation" source file
    make devtest             ;#  Run some tests (requires Tcl)