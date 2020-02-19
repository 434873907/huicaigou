<template>
  <div class="api-input-control">
    <input
      class="form-control"
      v-bind="$attrs"
      :type="type"
      :disabled="inputDisabled"
      :readonly="readonly"
      :autocomplete="autoComplete || autocomplete"
      ref="input"
      @input="handleInput"
      @focus="handleFocus"
      @blur="handleBlur"
      @change="handleChange"
      @click.stop
    >
  </div>
</template>

<script>
export default {

  data() {
    return {
      focused: false,
      valueTimer:'',
      nativeInputValue:''
    }
  },

  props: {
    value: [String, Number],
    disabled: Boolean,
    readonly: Boolean,
    regValue:{
      type: String,
      default:'number'
    },
    type: {
      type: String,
      default: 'text'
    },
    autocomplete: {
      type: String,
      default: 'off'
    },
    /** @Deprecated in next major version */
    autoComplete: {
      type: String
    }
  },

  computed:{
    inputDisabled() {
      return this.disabled;
    }
  },
  watch:{
    value(val){
      this.$nextTick(()=>{
        this.$refs.input.value = val
      })
    }
  },
  methods:{
    fixValue(value){
      if(this.regValue != 'number') return;
      if(value.indexOf('.') == value.length - 1){
        value = value.slice(0,-1);
        this.$refs.input.value = this.value;
        this.$emit('input', value);
      }
    },
    handleInput(event) {
      if (event.target.value === this.nativeInputValue) return;
      if(this.regValue == 'number'){
        if(event.target.value && !/^\d+(\.)?(\d+)?$/.test(event.target.value)){
          event.target.value = this.nativeInputValue;
          return;
        }else{
          this.nativeInputValue =  event.target.value;
        }
        if(!isNaN(event.target.value) && event.target.value.length > 3 &&  Number(event.target.value) < 0.01){
          this.$message('输入的数值过小，请重新输入');
        }
      }
      this.$emit('input', event.target.value);
    },
    handleChange(event) {
      this.$emit('change', event.target.value);
    },
    handleFocus(event) {
      this.focused = true;
      this.$emit('focus', event);
    },
    handleBlur(event) {
      this.focused = false;
      this.$emit('blur', event);
      this.fixValue(event.target.value);
    }
  },
  mounted() {
    if(this.value){
      this.nativeInputValue = this.value;
      this.$refs.input.value = this.value;
    }
  }
}
</script>

<style lang="scss">
.api-input-control{
  display: inline-block;
  .form-control{
    width: 100%;
  }
}
</style>
