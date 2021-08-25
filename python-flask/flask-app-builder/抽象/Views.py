# BaseView 基础的View, 和数据库无关, 和表单无关

class MyView(BaseView):
    route_base = "/myview"

    @expose('/method1/<string:param1>')
    @has_access
    def method1(self, param1):
        # do something with param1
        # and return it
        param1 = 'Goodbye %s' % (param1)
        self.update_redirect()
        return self.render_template('method3.html',
                                    param1=param1)

    @expose('/method2/<string:param1>')
    def method2(self, param1):
        # do something with param1
        # and render it
        param1 = 'Hello %s' % (param1)
        return param1

    @expose('/method3/<string:param1>')
    @has_access
    def method3(self, param1):
        # do something with param1
        # and render template with param
        param1 = 'Goodbye %s' % (param1)
        self.update_redirect()
        return self.render_template('method3.html',
                                    param1=param1)

# FormView 不带数据库, 包含表单, !! 不要标记url, 直接能把view注册到appbuilder
class MyForm(DynamicForm):
    field1 = StringField(('Field1'),
                         description=('Your field number one!'),
                         validators=[DataRequired()], widget=BS3TextFieldWidget())
    field2 = StringField(('Field2'),
                         description=('Your field number two!'), widget=BS3TextFieldWidget())


class MyFormView(SimpleFormView):
    form = MyForm
    form_title = 'This is my first form view'
    message = 'My form submitted'

    def form_get(self, form):
        form.field1.data = 'This was prefilled'

    def form_post(self, form):
        # post process form
        flash(self.message, 'info')

# ModelView 绑定数据库模型
class ContactGroup(Model):
    id = Column(Integer, primary_key=True)
    name = Column(String(50), unique = True, nullable=False)

    def __repr__(self):
        return self.name

class Contact(Model):
    id = Column(Integer, primary_key=True)
    name =  Column(String(150), unique = True, nullable=False)
    address =  Column(String(564), default='Street ')
    birthday = Column(Date)
    personal_phone = Column(String(20))
    personal_cellphone = Column(String(20))
    contact_group_id = Column(Integer, ForeignKey('contact_group.id'))
    contact_group = relationship("ContactGroup")

    def __repr__(self):
        return self.name

class GroupModelView(ModelView):
    # 模型关联视图
    datamodel = SQLAInterface(ContactGroup)
    related_views = [ContactModelView]

class ContactModelView(ModelView):
    # 模型关联视图
    datamodel = SQLAInterface(Contact)

    label_columns = {'contact_group':'Contacts Group'}
    list_columns = ['name','personal_cellphone','birthday','contact_group']

    show_fieldsets = [
        (
            'Summary',
            {'fields': ['name', 'address', 'contact_group']}
        ),
        (
            'Personal Info',
            {'fields': ['birthday', 'personal_phone', 'personal_cellphone'], 'expanded': False}
        ),
    ]