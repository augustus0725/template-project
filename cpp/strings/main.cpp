#include <iostream>

int main() {
    // Get a pointer to the current locale.
    char* locale = setlocale(LC_ALL, nullptr);

    // Print the current locale.
    printf("The current locale is: %s\n", locale);

    // Set locale
    setlocale(LC_ALL, "en_US.UTF-8");
    locale = setlocale(LC_ALL, nullptr);
    printf("The current locale is: %s\n", locale);

    std::cout << "ni hao, 中国" << std::endl;
    return 0;
}
