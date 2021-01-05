import axios from 'axios';
import {Header} from './Header';

export async function getSelectedMember() {
    const response = await axios.get<Header<Member>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/member/select`);
    return response.data;
}

export type Member = {
    member_id:        number;
    comment:         string;
    file_url:        string;
    file_origin_name:  string;
    sub_introduction: string;
    introduction:    string;
    phone_number:     string;
    email:           string;
}