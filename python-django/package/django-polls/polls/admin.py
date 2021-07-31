from django.contrib import admin

# Register your models here.

from .models import Question, Choice


# admin.site.register(Question)

# 定制admin模块


# class QuestionAdmin(admin.ModelAdmin):
#     fields = ['pub_date', 'question_text']

class QuestionAdmin(admin.ModelAdmin):
    fieldsets = [
        (None,               {'fields': ['question_text']}),
        ('Date information', {'fields': ['pub_date']}),
    ]


admin.site.register(Question, QuestionAdmin)
admin.site.register(Choice)
