# ��������
fossil clone https://www.sqlite.org/src sqlite.fossil

# sqlite.fossil �Ǹ�100MB+���ļ�
fossil open sqlite.fossil

# �л�branch
fossil checkout branch-3.42

# checkout ָ���汾�Ĵ��� such as "version-3.8.8"
fossil update VERSION

# ����sqlite
    tar xzf sqlite.tar.gz    ;#  Unpack the source tree into "sqlite"
    mkdir bld                ;#  Build will occur in a sibling directory
    cd bld                   ;#  Change to the build directory
    ../sqlite/configure      ;#  Run the configure script
    make                     ;#  Builds the "sqlite3" command-line tool
    make sqlite3.c           ;#  Build the "amalgamation" source file
    make devtest             ;#  Run some tests (requires Tcl)