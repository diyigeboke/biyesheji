package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String>  redisTemplate;

    //调用service方法实现登录
    @Override
    public String login(UcenterMember member) {

        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileMember == null){//没有这个手机号
            throw new GuliException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"登录失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
        if (StringUtils.isEmpty(code)){
            throw new GuliException(20001,"验证码已过期");
        }
        if (!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
                wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new GuliException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/3HEmJwpSzguqqAyzmBwqT6aicIanswZibEOicQInQJI3ZY1qmu59icJC6N7SahKqWYv24GvX5KH2fibwt0mPWcTJ3fg/132");
        baseMapper.insert(member);


    }

    //根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String oppenid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",oppenid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }


}
