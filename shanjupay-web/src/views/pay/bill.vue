<template>
  <div class="bill-container">
      <h3>应用财务对账</h3>
    <el-table
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      :header-cell-style="{color:'#5373e0',background:'#f3f6fb'}"
      style="width: 100%"
      v-if="isshow"
    >
      <el-table-column align="center" label="日期">
        <template slot-scope="scope">
          <span>{{scope.row.transactionTime}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="收入(元)">
        <template slot-scope="scope">
          <span>{{scope.row.income}}</span>
        </template>
      </el-table-column>

 
      <el-table-column align="center" label="笔数">
        <template slot-scope="scope">
          <span>{{scope.row.incomeNo}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="支出(元)">
        <template slot-scope="scope">
          <span>{{scope.row.expense}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="笔数">
        <template slot-scope="scope">
          <span>{{scope.row.expenseNo}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="应结金额">
        <template slot-scope="scope">
          <span>{{scope.row.payAmount}}</span>
        </template>
      </el-table-column>


      <el-table-column
      fixed="right"
      label="操作"
      width="100"
      align="center">
      <template slot-scope="scope">
        <el-button @click="detailClick(scope.row)" type="text" size="small">查看明细</el-button>
      </template>
    </el-table-column>
    </el-table>

    <el-table
      v-loading="listLoading"
      :data="detailList"
      fit
      highlight-current-row
      :header-cell-style="{color:'#5373e0',background:'#f3f6fb'}"
      style="width: 100%"
      v-if="!isshow"
    >
      <el-table-column align="center" label="订单号">
        <template slot-scope="scope">
          <span>{{scope.row.tradeNo}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="交易时间">
        <template slot-scope="scope">
          <span>{{scope.row.transactionTime}}</span>
        </template>
      </el-table-column>

 
      <el-table-column align="center" label="渠道">
        <template slot-scope="scope">
          <span>{{scope.row.payChannel}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额">
        <template slot-scope="scope">
          <span>{{scope.row.totalAmount}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="实付金额">
        <template slot-scope="scope">
          <span>{{scope.row.paymentAmount}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="手续费">
        <template slot-scope="scope">
          <span>{{scope.row.fee}}</span>
        </template>
      </el-table-column>
       <el-table-column align="center" label="实收金额">
        <template slot-scope="scope">
          <span>{{scope.row.receiveAmount}}</span>
        </template>
      </el-table-column>

       <el-table-column align="center" label="支付状态">
        <template slot-scope="scope">
          <span>{{scope.row.tradeState}}</span>
        </template>
      </el-table-column>

      <el-table-column
      fixed="right"
      label="操作"
      width="100"
      align="center">
      <template slot-scope="scope">
        <el-button @click="backList(scope.row)" type="text" size="small">返回列表</el-button>
      </template>
    </el-table-column>

    </el-table>
     

    <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.pageIndex"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    /> -->
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { Route } from 'vue-router'
import { Dictionary } from 'vuex'
import { login } from '@/api/users'
import { Form as ElForm, Input } from 'element-ui'
import { UserModule } from '@/store/modules/user'
import { useMoney,useMoneyDetail } from '@/api/pay.ts'

@Component({
  name: 'bill'
})
export default class extends Vue {
  private list: any[] = []
  private isshow: boolean = true;
  private detailList: any[] = []
  private listLoading: boolean = false
  created () {
    this.getList()
  }

  private async getList () {
    this.listLoading = true;
    this.list = await useMoney()
    this.listLoading = false;

  
  }

  private async detailClick (value:any) {
    this.isshow = false;
    this.listLoading = true;
    this.detailList = await useMoneyDetail(value.transactionTime)
    this.listLoading = false;

  }

  private backList () {
    this.isshow = true;
  }
}
</script>

<style lang="scss">
  .bill-container {
    width: 1200px;
    margin: 00 auto;
    padding: 42px 44px;
    background: white;
    height: 100%;
  }
</style>

<style lang="scss" scoped>

</style>
