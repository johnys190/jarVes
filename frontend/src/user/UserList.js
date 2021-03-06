import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import { 
      Table,
      Popconfirm,
      Button,
      notification,
      message
} from 'antd';
import { formatHumanDate } from '../util/Helpers';
import { deleteUserById, getAllUsers } from '../util/APIUtils';

class UserList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            columns: [{
                  title: 'Name',
                  dataIndex: 'name',
                  key: 'name',
                  sorter: (a, b) => a.name.localeCompare(b.name),
                  render: (name, record) =>{
                        return (
                              <Link to={{ pathname: "/users/" + record.id, state:{user: this.record} }}>{name}</Link>
                              );
                  },
                  width: 150,
                  fixed: 'left',
            },{
                title: 'Last Name',
                dataIndex: 'lastName',
                key: 'lastName',
                sorter: (a, b) => a.lastName.localeCompare(b.lastName),
            },{
                title: 'Email',
                dataIndex: 'email',
                key: 'email',
                sorter: (a, b) => a.email.localeCompare(b.email),
            },{
                  key: 'delete',
                  render: (record) => {
                        return (
                              <Popconfirm title="Are you sure delete this user?"
                                    onConfirm={this.deleteRecord.bind(this, record.id)}>
                                          <Button type="danger">Delete</Button>
                              </Popconfirm>
                  )},
                  width: 100,
                  fixed: 'right',
            }],
            dataSource:[]
        };
    }

    componentWillMount(){
        this.getAllUsers();
    }

    getAllUsers(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllUsers();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    dataSource: response ? response : [],
                    isLoading: false
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

      deleteRecord(id){

            let promise;

            promise = deleteUserById(id);

            const dataSource = this.state.dataSource.filter(i => i.id !== id)
            this.setState({dataSource})

            message.success('Deleted');

      }

    render(){
            if(this.state.isLoading) {
                  return <LoadingIndicator />
            }

        return (   
            <div>
                <Button type="primary" className="add-button">
                  <Link to={{ pathname: "/users/new" }}>
                  New User
                  </Link>
                </Button>
                <Table 
                    columns={this.state.columns}
                    dataSource={this.state.dataSource}/>
            </div>
            );
    }
}
export default withRouter(UserList);



