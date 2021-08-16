# -*- coding: utf-8 -*-
from datetime import datetime

from flask import session, flash, redirect, url_for, render_template

from app import db
from app.main import main
from app.main.forms import NameForm
from app.models import User


@main.route('/', methods=['GET', 'POST'])
def index():
    form = NameForm()
    if form.validate_on_submit():
        # name = form.name.data
        # form.name.data = ''
        user = User.query.filter_by(username=form.name.data).first()
        if not user:
            user = User(username=form.name.data)
            db.session.add(user)
            db.session.commit()
            session['known'] = False
            flash("欢迎新用户%r!!" % form.name.data)
        else:
            session['known'] = True
        session['name'] = form.name.data
        return redirect(url_for('main.index'))
    return render_template('index.html', form=form, name=session.get('name'), current_time=datetime.utcnow(),
                           known=session.get('known', False))

