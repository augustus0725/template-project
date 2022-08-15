# -*- coding: utf-8 -*-
from behave import *


@given('已经有了浏览器')
def step_impl(context):
    pass


@when('打开浏览器')
def step_impl(context):
    assert True is not False


@then('我们能输入地址')
def step_impl(context):
    assert context.failed is False
