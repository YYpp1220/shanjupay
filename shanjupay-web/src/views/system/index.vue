<template>
  <div class="data-container">
    <h3>角色管理</h3>
    <el-row type="flex" justify="end" class="add">
      <el-button type="primary" size="medium" @click="openSkuDialog" id="addBtn">新增</el-button>
    </el-row>

    <el-form :inline="true" size="medium" class="forms">
      <el-form-item label="账户/用户名:">
        <el-input v-model="skuName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchSku" id="searchBtn">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      :header-cell-style="{color:'#5373e0',background:'#f3f6fb'}"
      style="width: 70%"
      class="tables"
    >
      <el-table-column align="center" label="角色编号">
        <template slot-scope="scope">
          <span>{{scope.row.code}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="角色名称">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="120" align="center">
        <template slot-scope="scope">
          <el-button @click="updateClick(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="delClick(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="trees">
      <h4>权限分配</h4>
      <el-tree
        :data="data"
        show-checkbox
        node-key="id"
        ref="tree"
        highlight-current
        :props="defaultProps"
        @check-change="getdata"
        style="width:200px"
      ></el-tree>
    </div>

    <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.pageIndex"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    />-->
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { Route } from 'vue-router'
import { Dictionary } from 'vuex'
import { login } from '@/api/users'
import { Message } from 'element-ui'
import { Form as ElForm, Input } from 'element-ui'
import { UserModule } from '@/store/modules/user'
import { getRolesList, delRoles } from '@/api/system.ts'

@Component({
  name: 'index'
})
export default class extends Vue {
  private list: any[] = []
  private pageNo: number = 1
  private pageSize: number = 10
  private data: any[] = [
    {
      id: 1,
      label: '账户管理',
      children: [
        {
          id: 8,
          label: '账户中心'
        },
        {
          id: 9,
          label: '资质申请'
        }
      ]
    },
    {
      id: 2,
      label: '支付应用管理',
      children: [
        {
          id: 10,
          label: '应用管理'
        },
        {
          id: 11,
          label: '开始支付'
        },
        {
          id: 12,
          label: '应用财务对账'
        },
        {
          id: 13,
          label: '应用交易总览'
        }
      ]
    },
    {
      id: 3,
      label: '组织管理',
      children: [
        {
          id: 14,
          label: '门店管理'
        },
        {
          id: 15,
          label: '成员管理'
        }
      ]
    },
    {
      id: 4,
      label: '企业管理',
      children: [
        {
          id: 16,
          label: '企业管理'
        },
        {
          id: 17,
          label: '审核管理'
        },
        {
          id: 18,
          label: '会员管理'
        }
      ]
    },
    {
      id: 5,
      label: '服务类型管理',
      children: [
        {
          id: 19,
          label: '服务类型管理'
        }
      ]
    },
    {
      id: 6,
      label: '系统管理',
      children: [
        {
          id: 20,
          label: '角色管理'
        },
        {
          id: 21,
          label: '管理员管理'
        }
      ]
    },
    {
      id: 7,
      label: '账单管理',
      children: [
        {
          id: 22,
          label: '往来对账'
        }
      ]
    }
  ]
  private bodys = {

  }
  created() {
    this.getList()
    console.log(this.$router)
  }

  private async getList() {
    let data = await getRolesList(this.bodys,'')

    this.list = data.items
  }
  private getdata(value: any) {
    console.log(this.$refs.tree.getCheckedNodes())
  }
  private async delClick(value: any) {
    let res = await delRoles(value.id)
    if (!res) {
      Message({
        message: '删除失败',
        type: 'error',
        duration: 2 * 1000
      })
    } else {
      Message({
        message: '删除成功',
        type: 'info',
        duration: 2 * 1000
      })

      this.getList()
    }
  }
}
</script>

<style lang="scss">
.data-container {
  width: 1200px;
  margin: 00 auto;
  padding: 42px 44px;
  background: white;
  height: 100%;
  overflow: hidden;

  .tables {
    float: left;
  }
  .trees {
    float: left;
    margin-left: 70px;
    margin-top: -20px;
  }
}
</style>

<style lang="scss" scoped>
</style>
