# ormģ��
pnpm add prisma
pnpm add prisma/client

# ����libĿ¼, �����һЩutil
pnpm add prisma
pnpm add @prisma/client
pnpm add @auth/prisma-adapter

db.js

==> pnxp prisma init �������ݿ������
- ��.env�ļ������� DATABASE_URL
- ��schema.prisma ������ database block
  - postgresql
  - mysql
  - sqlite
  - sqlserver
  - mongodb
  - cockroachdb
- ���� pnxp prisma db pull �����ݿ����schemaд��prisma schema
- ���� pnxp prisma generate ���� prisma client ִ�����ݿ����
- ���� pnxp prisma db push �����ݿ������ɱ�ṹ

==> bcrypt: ���������
pnpm add @types/bcrypt

--------------------------------
ʹ��next-auth v5 �İ汾 
--------------------------------
pnpm add next-auth@beta 

--------------------------------
����ѧϰ prisma 
--------------------------------
pnpm add prisma
pnpx prisma init --database-provider postgresql

==> ���һЩmodel֮��
pnpx prisma migrate dev --name init
// ɾ����������
pnpx prisma migrate reset
// ��������һ��web,�鿴���ݿ�Ľṹ
pnpx prisma studio


--------------------------------
����ѧϰ zod  (����������ݽ�����֤)
--------------------------------
pnpm add zod
// ���Ҳ���л�����validate����, ���ǹ���û��zodȫ, ����email�ļ��
pnpm add react-hook-form
// ���� zod & react-hook-form
pnpm add @hookform/resolvers


---------------------------------------
����ѧϰ tailwindcss -> tailwind-merge
---------------------------------------
// ��װ
pnpm add tailwindcss-merge
// �����ͨ������ķ���������ʽ
const className = twMerge('border rounded px-2 py-1', props.className)
return <input {...props} className={className} />

---------------------------------------------
����ѧϰ tailwindcss -> tailwindcss-animate
---------------------------------------------
// ��װ
pnpm add tailwindcss-animate
// ���� tailwind.config.js
module.exports = {
	theme: {
		// ...
	},
	plugins: [
		require("tailwindcss-animate"),
		// ...
	],
}

// ��һЩ����Ч��
<!-- Add an animated fade and zoom entrance -->
<div class="animate-in fade-in zoom-in">...</div>

<!-- Add an animated slide to top-left exit -->
<div class="animate-out slide-out-to-top slide-out-to-left">...</div>

<!-- Control animation duration -->
<div class="... duration-300">...</div>

<!-- Control animation delay -->
<div class="... delay-150">...</div>

----------------------------------------------------------------
����ѧϰ clsx
utils����, �ϲ�css�����õ�, ���Ժ�tailwindcss���
----------------------------------------------------------------
clsx('foo', [1 && 'bar', { baz:false, bat:null }, ['hello', ['world']]], 'cya');
//=> 'foo bar hello world cya'

import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

----------------------------------------------------------------
next-auth���github�Խ�
----------------------------------------------------------------
callback��urlӦ����: http://localhost:3001/api/auth/callback/github
==> ���е�callback url���Դ� http://localhost:3001/api/auth/providers ��ȡ

----------------------------------------------------------------
next-auth���Լ��Զ����credentials
----------------------------------------------------------------

----------------------------------------------------------------
next-auth �� prisma�İ�, ʵ��ͨ��������֤���
----------------------------------------------------------------
pnpm add @auth/prisma-adapter
// �Ա��ϵ�������м���
pnpm add bcryptjs







