#include "gtest/gtest.h"
#include "hello.h"


// Demonstrate some basic assertions.
TEST(HelloTest, BasicAssertions) {
// Expect two strings not to be equal.
    EXPECT_STRNE("hello", "world");
// Expect equality.
    EXPECT_EQ(7 * 6, 42);
    EXPECT_EQ(3, add(1, 3));
}

TEST(HelloTest, FeatureOfAdd) {
    EXPECT_EQ(100, add(50, 50));
}