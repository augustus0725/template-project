# -*- coding: utf-8 -*-
from flask import Flask
from markupsafe import escape

app = Flask(__name__)

@app.route('/user/<username>')
def show_user_profile(username):
    # show the user profile for that user
    return {
        'welcome': 'User %s' % escape(username)
    }

@app.route('/user/<username>', methods=['POST'])
def welcome(username):
    # show the user profile for that user
    return 'User %s' % escape(username)
