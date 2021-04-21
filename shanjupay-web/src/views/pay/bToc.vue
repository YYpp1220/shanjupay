<template>
  <el-dialog title="支付设置" :visible.sync="syncDialogVisible" @open="opend" width="500px">
    <el-form :inline="false">
      <el-form-item label="支付码：" :label-width="formLabelWidth" :rules="[{required:true}]">
        <el-input ref="inps" id="inps" v-model="codes" autofocus @input="changes"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="ensureDialog">确定</el-button>
    </div>
    <!-- <Scan :visible.sync="dialogVisibles"></Scan> -->
  </el-dialog>
</template>

<script lang="ts">
import { Component, Vue, PropSync, Emit, Prop } from "vue-property-decorator";
import { Message } from "element-ui";
import { constants } from "fs";
import { getAppList } from "@/api/pay.ts";
import { getStoresData } from "@/api/organization.ts";
import { BscanC } from "@/api/pay.ts";
import Scan from "@/views/pay/scan.vue";
@Component({
  components: {
    Scan
  }
})
export default class extends Vue {
  @PropSync("dialogVisible", { type: Boolean, default: false })
  syncDialogVisible!: boolean;
  @PropSync("subject", { type: String })
  subjects!: string;
  @PropSync("totalAmount", { type: String })
  totalAmounts!: string;
  formLabelWidth = "100px";
  private pageNo: number = 1;
  private pageSize: number = 10;
  private appId: string = "";
  private storeId: string = "";
  private dialogVisibles: boolean = false;
  private codes: string = "";
  private inputs: string = "";
  private form = {
    appId: "",
    authCode: "",
    totalAmount: "",
    storeId: "",
    subject: "",
    body: ""
  };

  private list = [];
  private storeList = [];
  created() {
    this.getList();
    this.$nextTick(() => {
      console.log(this.$refs.inps)
    });
  }
  mounted() {
    
    
  }
  private changes(value: any) {}
  private async opend() {
    // let res = await BscanC(this.form)
    this.appId = localStorage.getItem("appId")!;
    this.storeId = localStorage.getItem("storeId")!;
    document.onkeydown = this.changes;
    // function keyDown(e:any){
    // //IE内核浏览器
    //  if (navigator.appName == 'Microsoft Internet Explorer'){
    //          var keycode = event.keyCode;
    //          var realkey = String.fromCharCode(event.keyCode);
    //    }else {//非IE内核浏览器
    //          var keycode = e.which;
    //          var realkey = String.fromCharCode(e.which);
    //    }
    //    // console.log('按键码:' + keycode +  '字符: ' + realkey);

    //    _this.inputs.change(function(){
    //        console.log(1111);

    //    })

    //    document.onkeydown = keyDown

    // }
  }
  private async getList() {}
  private async ensureDialog() {
    this.syncDialogVisible = false;
    const loading = this.$loading({
      lock: true,
      text: "支付中",
      spinner: "el-icon-loading",
      background: "rgba(255, 255, 255, 0.9)"
    });
    this.form.appId = this.appId;
    this.form.storeId = this.storeId;
    this.form.authCode = this.codes;
    this.form.totalAmount = this.totalAmounts;
    this.form.subject = this.subjects;
    this.form.body = this.subjects;
    let res = await BscanC(this.form);
    if (res.code === '0') {
      this.$message.success('交易成功')
      loading.close();
    } else {
      this.$message.error('交易失败')
      loading.close();
    }
    this.codes = ''
    this.dialogVisibles = true;
  }

}
</script>

<style lang="scss" scoped>
     .el-loading-mask .el-loading-spinner .el-loading-text {
        font-size: 30px!important
      }
      .el-loading-spinner i {
        font-size: 30px!important;
        
      }
</style>


