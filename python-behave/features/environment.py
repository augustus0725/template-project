# -*- coding: utf-8 -*-
from behave import fixture, use_fixture
from selenium import webdriver

from fixtures import selenium_browser_chrome


def before_all(context):
    use_fixture(selenium_browser_chrome, context)
    # -- HINT: CLEANUP-FIXTURE is performed after after_all() hook is called.
