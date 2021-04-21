<template>
  <el-dialog title="修改门店信息" :visible.sync="syncDialogVisible" @open="opend" @close="closed">
    <el-form :inline="false">
      <el-form-item label="门店名称：" :label-width="formLabelWidth" :rules="[{required:true}]">
        <el-input v-model="form.storeName"></el-input>
      </el-form-item >

      <el-form-item label="门店地址：" :label-width="formLabelWidth">
        <el-input v-model="form.storeAddress"></el-input>
      </el-form-item>

      <el-form-item label="门店编号：" :label-width="formLabelWidth">
        <el-input v-model="form.storeNumber"></el-input>
      </el-form-item>
      <el-form-item label="管理员：" :label-width="formLabelWidth">
          <el-select v-model="list" multiple placeholder="请选择">
                <el-option
                v-for="item in memberList"
                :key="item.id"
                :label="item.username"
                :value="item.id">
                </el-option>
            </el-select>
      </el-form-item >
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="syncDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="ensureDialog">确 定</el-button>
    </div>
    
  </el-dialog>
</template>

<script lang="ts">
import { Component, Vue, PropSync, Emit, Prop } from 'vue-property-decorator'
import { Message } from 'element-ui'
import { constants } from 'fs';
import { updateStore, getStoresData,searchStore,getMemberData} from '@/api/organization.ts'
@Component({
  components:{
    
  }
})
export default class createTasks extends Vue {
  @PropSync('dialogVisible', { type: Boolean, default: false })
  syncDialogVisible!: boolean
  @PropSync('storeIds',{type:String})
  storeId!:string
  formLabelWidth: string = '100px'
  private form = {
      storeAddress:'',
      storeName:'',
      storeNumber:0,
      parentId:null,
      storeStatus:'',
      merchantId:null,
      id:''

  }
  private storeForm = {
    storeNumber:'',
    storeName:''
  }
  private memberForm = {
    username:''
  }
  private listData:any [] = []
  private filterData:any [] = []
  private storeAdmin:any[] = []
  private list:any[] = []
  private memberList:any[] = []
  private opend () {
    this.getList()
  }
  private async getList () {
    let data = await getMemberData(1,10,this.memberForm)
    this.memberList  = data.items
    this.storeAdmin = await searchStore(this.storeId)
    console.log(this.storeAdmin.staffs)
    if (this.storeAdmin.staffs != null) {
      this.storeAdmin.staffs.forEach((item:any) => {
        this.list.push(item.id)
      })
    }
    this.form.storeName = this.storeAdmin.storeName
    this.form.storeAddress = this.storeAdmin.storeAddress
    this.form.storeNumber = this.storeAdmin.storeNumber
    this.form.id = this.storeId
  }
  private closed () {
    this.list = []
  }
  private async ensureDialog () {
    if (this.form.storeAddress === '' || this.form.storeName === '') {
      this.$message.error('必填项不能为空');return;
    } 
    let res = await updateStore(this.form,this.list)
    if (!res) {
      this.$message.success('修改成功')
      this.$emit('refreshList')
    }
    this.syncDialogVisible = false;
    
  }
}
</script>
