<template>
  <el-dialog title="修改成员信息" :visible.sync="syncDialogVisible" @open="opend" @close="closed">
    <el-form :inline="false">
      <el-form-item label="姓名：" :label-width="formLabelWidth" :rules="[{required:true}]">
        <el-input v-model="form.fullName"></el-input>
      </el-form-item>
      <el-form-item label="职位：" :label-width="formLabelWidth" :rules="[{required:true}]">
        <el-input v-model="form.position"></el-input>
      </el-form-item>
      <el-form-item label="归属门店"  :label-width="formLabelWidth" :rules="[{required:true}]" >
          <el-select v-model="form.storeId" placeholder="请选择">
                <el-option
                v-for="item in storeList"
                :key="item.id"
                :label="item.storeName"
                :value="item.id">
                </el-option>
            </el-select>
      </el-form-item >
        <el-form-item label="角色：" :label-width="formLabelWidth" :rules="[{required:true}]">
          <el-select v-model="rolecode" multiple placeholder="请选择" @focus="focusd">
                <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.name"
                :value="item.code">
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
import {updateMember,getMemberData,getStoresData,getRole,searchMemberData } from '@/api/organization.ts'
@Component({
  components:{ 
  }
})
export default class  extends Vue {
  @PropSync('dialogVisible', { type: Boolean, default: false })
  syncDialogVisible!: boolean
  @PropSync('memberIds',{type:String})
  memberId!:string
  formLabelWidth: string = '100px'
  private form = {
      fullName:'',
      position:'',
      storeId:'',
      id:''
  }
  private rolecode:any[] = []
  private listData: any [] = []
  private filterData: any [] = []
  private roleList:any[] = []
  private storeList:any[] = []
  private memberAdmin = {}
  private opend () {
    this.getList()
  }
  private memberFrom = {
    username:''
  }
   private storeForm = {
    storeName:'',
    storeNumber:''
  }
  private async getList () {

    this.memberAdmin = await searchMemberData(this.memberId)
    this.roleList = await getRole()
    let data = await getStoresData(1,10,this.storeForm)
    this.storeList = data.items
    this.form.fullName = this.memberAdmin.fullName
    this.form.position = this.memberAdmin.position
    this.form.storeId = this.memberAdmin.storeId
    this.form.id = this.memberId
    console.log(Number(this.form.storeId))
    this.memberAdmin.roles.forEach((item:any) => {
      this.rolecode.push(item.code)
    })
  }
  private closed () {
    this.rolecode = []
  }
  private focusd () {
    this.rolecode = []
  }
  private async ensureDialog () {

    if(this.form.storeId === '' || this.rolecode.length === 0|| this.form.fullName ==='' || this.form.position=== '') {
      this.$message.error('必填项不能为空');return;
    }
    let res = await updateMember(this.form,this.rolecode)
    if (!res) {
      this.$message.success('修改成功')
      this.$emit('refreshList')
    }
    this.rolecode = []
    this.syncDialogVisible = false;
  }
}
</script>
