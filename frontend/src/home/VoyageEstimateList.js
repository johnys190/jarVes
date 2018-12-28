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
import { deleteVoyEstimateById, getAllVoyEstimates } from '../util/APIUtils';

class VoyageEstimateList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            columns: [{
                  title: 'Name',
                  dataIndex: 'name',
                  key: 'name',
                  render: (name, record) =>{
                        return (
                              <Link to={{ pathname: "/voyageEstimate/" + record.id, state:{voyageEstimate: this.record} }}>{name}</Link>
                              );
                  }
            },{
                title: 'Vessel',
                dataIndex: 'vessel',
                key: 'vessel',
            },{
                title: 'Broker',
                dataIndex: 'broker',
                key: 'broker',
            },{
                title: 'Voyage',
                dataIndex: 'voyage',
                key: 'voyage',
            },{
                title: 'Account',
                dataIndex: 'account',
                key: 'account',
            },{
                title: 'Commodity',
                dataIndex: 'commodity',
                key: 'commodity',
            },{
                  key: 'delete',
                  render: (record) => {
                        return (
                              <Popconfirm title="Are you sure delete this?"
                                    onConfirm={this.deleteRecord.bind(this, record.id)}>
                                          <Button type="danger">Delete</Button>
                              </Popconfirm>
                  )}
            }],
            dataSource:[]
        };
    }

    componentWillMount(){
        this.getAllVoyEstimates();
    }

    getAllVoyEstimates(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllVoyEstimates();

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

            promise = deleteVoyEstimateById(id);

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
                <Button type="primary" className="add-button" href="/voyageEstimate/new">New Voyage Estimation</Button>
                <Table 
                    columns={this.state.columns}
                    dataSource={this.state.dataSource}/>
            </div>
            );
    }
}
export default withRouter(VoyageEstimateList);



