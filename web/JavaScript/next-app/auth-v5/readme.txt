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



















