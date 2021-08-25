from flask_babel import gettext as _

_.translation('.', '.', languages=['zh_CN']).install(True)

print(_("Hello"))
