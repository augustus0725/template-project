void fast_split(const char *source, char sep,
                char *slices[], int size,
                char *payload) {
    char *p = payload;

    strcpy(payload, source);
    for (int i = 0; i < size; ++i) {
        slices[i] = p;
        while (*p != '\0') {
            if (*p == sep) {
                *p = '\0';
                ++p;
                break;
            }
            ++p;
        }
    }
}

/**
    string str("ÖÐÎÄ,two,three");

    char *slices[5] = {0};
    char payload[128];

    fast_split(str.c_str(), ',', slices, 3, payload);

*/