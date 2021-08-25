# BaseApi

class ExampleApi(BaseApi):
    # http://127.0.0.1:5000/api/v1/exampleapi/greeting
    @expose('/greeting')
    def greeting(self):
        return self.response(200, message="Hello")

    @expose('/greeting3')
    # http://localhost:8080/api/v1/example/greeting3?q=(name:daniel)
    @rison()
    def greeting3(self, **kwargs):
        if 'name' in kwargs['rison']:
            return self.response(
                200,
                message=f"Hello {kwargs['rison']['name']}"
            )
        return self.response_400(message="Please send your name")

# ModelRestApi
class GroupModelApi(ModelRestApi):
    resource_name = 'group'
    datamodel = SQLAInterface(ContactGroup)
