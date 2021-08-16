# -*- coding: utf-8 -*-
from datetime import datetime

from flask import session, flash, redirect, url_for, render_template

from app import db
from app.main import main
from app.main.forms import NameForm
from app.models import User


@main.route('/', methods=['GET', 'POST'])
def index():
    return render_template('index.html')


@main.route('/user/<username>')
def user(username):
    user = User.query.filter_by(username=username).first_or_404()
    return render_template('user.html', user=user)
